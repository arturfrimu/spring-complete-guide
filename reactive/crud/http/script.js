const BASE_URL = "http://localhost:8787/api/v1/developers";

const jsonHeaders = {
  "Content-Type": "application/json",
};

async function createDeveloper(payload) {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: jsonHeaders,
    body: JSON.stringify(payload),
  });

  if (!res.ok) throw new Error(`Create failed: ${res.status} ${await res.text()}`);
  return res.json();
}

async function updateDeveloper(payload) {
  const res = await fetch(BASE_URL, {
    method: "PUT",
    headers: jsonHeaders,
    body: JSON.stringify(payload),
  });

  if (!res.ok) throw new Error(`Update failed: ${res.status} ${await res.text()}`);
  return res.json();
}

async function findAllDevelopers() {
  const res = await fetch(BASE_URL, { method: "GET" });

  if (!res.ok) throw new Error(`Find all failed: ${res.status} ${await res.text()}`);
  return res.json();
}

async function findDeveloperById(id) {
  const res = await fetch(`${BASE_URL}/${encodeURIComponent(id)}`, { method: "GET" });

  if (!res.ok) throw new Error(`Find by id failed: ${res.status} ${await res.text()}`);
  return res.json();
}

async function deleteDeveloperSoft(id) {
  const res = await fetch(`${BASE_URL}/${encodeURIComponent(id)}`, { method: "DELETE" });

  if (!res.ok) throw new Error(`Soft delete failed: ${res.status} ${await res.text()}`);
  return;
}

async function deleteDeveloperHard(id) {
  const res = await fetch(`${BASE_URL}/${encodeURIComponent(id)}?isHard=true`, {
    method: "DELETE",
  });

  if (!res.ok) throw new Error(`Hard delete failed: ${res.status} ${await res.text()}`);
  return;
}
