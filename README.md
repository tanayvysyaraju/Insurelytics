# **Insurelytics**
**Full-Stack Insurance & Investment Analytics Platform with Enterprise-Grade DevOps, Observability, and AI-Ready Capabilities**

---

## **Overview**
**Insurelytics** is a **Spring Boot + React** platform for managing insurance policies, tracking investments, and forecasting financial returns. It is production-oriented from day one: **Dockerized**, deployable to **Kubernetes via Helm**, fully **CI/CD-automated with GitHub Actions**, and instrumented via **Micrometer → Prometheus → Grafana** for **SRE-grade visibility**.

**Highlights**
- **Account Dashboard**: consolidated view of investments, premiums, current value, and projected growth  
- **Real-Time Returns**: compound/annuity models with optional risk adjustment  
- **Policy Comparison**: side-by-side comparison by cost, duration, and growth  
- **Policy Timeline**: milestones for premiums, lock-ins, and payouts  
- **Secure Auth**: JWT + Spring Security (BCrypt), RBAC-ready  
- **Observability**: Prometheus scraping + Grafana dashboards (system + business KPIs)

---

## **Features**

| # | **Feature** | **Description** |
|---|-------------|------------------|
| 1 | **Account Summary Dashboard** | Aggregate totals for invested, current value, upcoming premiums, projected growth |
| 2 | **Real-Time Return Calculator** | Compound/annuity projections; risk factor optional |
| 3 | **Policy Comparison Engine** | Compare premium, duration, expected return |
| 4 | **Visual Policy Timeline** | Milestones: due dates, lock-ins, payouts |
| 5 | **JWT Authentication** | Stateless tokens; role-based security ready |
| 6 | **CI/CD Pipeline** | GitHub Actions → Helm deploy to Kubernetes |
| 7 | **Grafana Monitoring** | Latency, traffic, errors, ROI via Prometheus/Micrometer |

> Tables are kept concise by design. Explanatory detail is in prose below.

---

## **Tech Stack**

### **Frontend**
- **React**, **React Router**, **Axios**
- **Material UI** components
- **Recharts** (or Chart.js) for data viz

### **Backend**
- **Spring Boot** (MVC, JPA, Security, WebClient)
- **Micrometer** + **Spring Actuator**
- **JWT** + **BCrypt**
- **DTO-based contracts**

### **Data**
- **PostgreSQL** (normalized: users, policies, investments, transactions)
- **Dockerized** for local & cloud

### **DevOps / Platform**
- **Docker** multi-stage builds (frontend & backend)
- **Kubernetes** with **Helm** values for envs/replicas/tags
- **GitHub Actions** CI/CD (tests → build → push → deploy)
- **Prometheus** + **Grafana** dashboards

---

## **Architecture**

![Architecture Diagram]

**Flow**
1. **Auth**: client logs in; backend issues **JWT**  
2. **API**: React calls Spring Boot with `Authorization: Bearer <token>`  
3. **Data**: services aggregate via **JPA** from **PostgreSQL**  
4. **Metrics**: `/actuator/prometheus` → **Prometheus** → **Grafana**  
5. **Delivery**: push to `main` → **GitHub Actions** → **Helm** to K8s

**Operational Traits**
- Stateless services; liveness/readiness probes  
- Env-driven config; Helm templating  
- Clear layering: controllers → services → repositories → entities/DTOs

---

## **Getting Started**

### **Prerequisites**
- **Docker** and **Docker Compose**
- **Java 17** and **Maven**
- **Node.js** (optional for non-Docker FE dev)
- **kubectl** and **Helm** (optional for K8s deploy)

### **Run Locally (Docker Compose)**
```bash
git clone https://github.com/<your-username>/Insurelytics.git
cd Insurelytics
docker compose -f docker-compose.local.yml up --build
```

**Default URLs**
- **Frontend**: http://localhost:3000  
- **Backend**:  http://localhost:8081  
- **Grafana**:  http://localhost:3001  (admin/admin)  
- **Prometheus**: http://localhost:9090

> Ports can be adjusted in `docker-compose.local.yml`.

### **First-Run Test**
```bash
# Register
curl -X POST http://localhost:8081/auth/register   -H "Content-Type: application/json"   -d '{"username":"demo","password":"demo"}'

# Login (capture JWT)
TOKEN=$(curl -s -X POST http://localhost:8081/auth/login   -H "Content-Type: application/json"   -d '{"username":"demo","password":"demo"}' | jq -r .token)

# Protected endpoint
curl -H "Authorization: Bearer $TOKEN" http://localhost:8081/dashboard/summary
```

---

## **Configuration**

**Environment variables** (compose or Helm values)
- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`
- `JWT_SECRET` (32+ chars recommended)

**Kubernetes via Helm**  
- Set `image.repository`, `image.tag`, `replicaCount`, and DB/JWT envs in `values.yaml`  
- `helm upgrade --install <release> ./helm`

---

## **Key API Endpoints**

| **Method** | **Endpoint** | **Purpose** |
|------------|--------------|-------------|
| GET | `/dashboard/summary` | Portfolio summary |
| POST | `/calculator/returns` | Returns projection |
| GET | `/policies` | List policies |
| GET | `/policies/compare?ids=1,2,3` | Compare policies |
| GET | `/policies/timeline` | Policy milestones |
| POST | `/auth/register` | Create account |
| POST | `/auth/login` | Issue JWT |

---

## **Testing**

**Backend**
```bash
cd backend
mvn test
```

**Frontend**
```bash
cd frontend
npm test
```

---

## **Observability**

**System**
- Request rate, error rate  
- Latency histograms (p50/p95/p99)  
- JVM memory/GC, thread metrics

**Business**
- Average ROI across users  
- Feature usage counters

**Viewing**
- Spring Actuator exposes metrics  
- Prometheus scrapes  
- Grafana dashboards render system + business KPIs

---

## **CI/CD Pipeline**

**Trigger**  
- Push to `main`

**Jobs**  
- Backend tests  
- Frontend build  
- Build/push images (GHCR/Docker Hub)  
- Deploy with `helm upgrade --install`

**Benefits**  
- Reproducible builds  
- Automated promotion  
- Rollbacks via Helm history

---

## **Security**

- **JWT** signature validation in auth filter  
- **BCrypt** password hashing  
- Least-privilege DB creds per env  
- Secrets provided at runtime (CI/CD, K8s secrets)  
- CORS and HTTP header hardening configurable

---

## **SRE & Production**

- Health/readiness probes for safe rollouts  
- Resource requests/limits; HPA-ready  
- Golden signals + dashboards  
- Container stdout logging (ELK/Datadog ready)  
- Optional **Resilience4j** for retries/limits/circuit breakers  
- Optional **Flyway** for schema migrations

---

## **Project Structure**
```text
insurelytics/
  backend/                  # Spring Boot app (MVC, JPA, Security, Actuator)
  frontend/                 # React app (Material UI, Recharts)
  helm/                     # Kubernetes Helm chart
  docker-compose.local.yml  # Local stack (FE/BE/DB/Prom/Grafana)
  prometheus.yml            # Prometheus scrape config (local)
  grafana/                  # Grafana dashboard provisioning (optional)
  README.md
```

---

## **Roadmap**
- Spring AI policy recommendations  
- Flyway migrations + seed data  
- OpenAPI/Swagger documentation  
- Advanced RBAC and admin console  
- Dark mode and extended charts

---

## **License**
**MIT** — free to use and modify.
