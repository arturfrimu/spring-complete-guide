### Step 1 pom.xml

```xml
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-envers</artifactId>
    <version>${hibernate-envers.version}</version> 
    <scope>compile</scope>
</dependency>
<!-- Alege versiunea compatibila cu versiunea de spring boot pe care o ai la moement -->
<!-- Verifica in ce an si luna a fost lansata versiunea de spring boot, si alege o versiune de hibernate envers tot din perioada ceia -->
```

### Step 2 application.properties sau application.yaml

```yaml
spring:
  jpa:
    properties:
      envers:
        audit_table_suffix: _AUD
        revision_field_name: REV
        revision_type_field_name: REVTYPE
```

### Step 3 Entity

```java
@EntityListeners(GenericAuditEntityListener.class)
public class YourEntity {
    // Add this fields after all fields in your entity
    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private Timestamp createdAt;
    @LastModifiedDate
    @Column(name = "UPTADED_AT")
    private Timestamp updatedAt;
    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;
    @LastModifiedBy
    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
```

### Step 4 creare tabela REVINFO

```sql
create table REVINFO(
    REV      numeric not null constraint PK_REVINFO primary key,
    REVTSTMP numeric not null
);
```

### Step 5 creare tabela de audit

```sql
create table YOUR_TABLE_NAME_AUD -- suffix _AUD
(
    REV                numeric not null constraint contstraint_name references REVINFO, -- schimba denumirea constraint-ului
    REVTYPE            numeric,
    ID                 numeric not null,
    
    -- all your table columns here     
    
    CREATED_AT         TIMESTAMP(6),
    UPDATED_AT         VARCHAR2(200),
    CREATED_BY         TIMESTAMP(6),
    UPDATED_BY         VARCHAR2(200),
    constraint PK_YOUR_TABLE_NAME_AUD primary key (REV, ID)
)
```

### Step 6 Adauga clasa de configurare ca envers sa stie cum sa extraga valori pentru campurile createdBy si updatedBy

```java
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            String user = Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getPrincipal)
                    .map(AuthUserInfo.class::cast)
                    .map(AuthUserInfo::getUsername)
                    .orElse("ANONYMOUS");
            return Optional.of(user);
        };
    }
}
```