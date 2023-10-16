# Transactional

## Isolation

_**Isolation.READ_UNCOMMITTED:**_ Acesta este cel mai slab nivel de izolare. Permite "Dirty Reads", "Non-Repeatable
Reads", și "Phantom Reads". Este rareori folosit din cauza riscului foarte mare de a citi date inconsistente.

_**Isolation.READ_COMMITTED:**_ Este nivelul implicit pentru multe sisteme de gestionare a bazelor de date, inclusiv
PostgreSQL. Acesta previne "Dirty Reads" dar permite "Non-Repeatable Reads" și "Phantom Reads".

_**Isolation.REPEATABLE_READ:**_ Acest nivel previne "Dirty Reads" și "Non-Repeatable Reads" dar permite "Phantom
Reads".

_**Isolation.SERIALIZABLE:**_ Acesta este cel mai puternic nivel de izolare și previne toate tipurile de probleme de
concurență: "Dirty Reads", "Non-Repeatable Reads", și "Phantom Reads". Cu toate acestea, acest nivel de izolare poate
avea un impact negativ asupra performanței, deoarece blochează mai multe resurse.