Insurelytics

A Full-Stack Insurance & Investment Analytics Platform with Enterprise-Grade DevOps, Observability, and AI-Ready Capabilities

Overview

Insurelytics is a Spring Boot + React platform for managing insurance policies, tracking investments, and forecasting financial returns. It is production-minded from the start: Dockerized, deployable to Kubernetes via Helm, fully CI/CD-automated with GitHub Actions, and instrumented with Micrometer → Prometheus → Grafana for SRE-grade visibility.

Core functionality:
	•	Dynamic account dashboard summarizing policies, investments, premiums, and projected growth
	•	Real-time return calculator with compound/annuity models and optional risk adjustments
	•	Policy comparison engine for side-by-side evaluation
	•	Visual policy timeline highlighting milestones and payouts
	•	JWT-based authentication and role-ready authorization
	•	Grafana dashboards tracking both system health and business KPIs

⸻

Features

1. Account Summary Dashboard

What: Aggregated view of total invested, current value, upcoming premiums, and projected growth.
How: GET /dashboard/summary orchestrates JPA queries across Policy, Investment, and Transaction entities, then returns a DTO optimized for the frontend.
Why it matters: Demonstrates service layering, DTO usage, and aggregation logic for read-heavy endpoints.

2. Real-Time Return Calculator

What: Simulates returns over time with compound interest and annuity formulas, optionally including policy-specific risk factors.
How: POST /calculator/returns executes a calculation service; the frontend renders the output dynamically in a chart.
Why it matters: Showcases separation of calculation logic from controllers and a responsive UI data flow.

3. Policy Comparison Engine

What: Compares selected policies by premium, duration, and expected return.
How: GET /policies/compare?ids=1,2,3 returns minimal, frontend-friendly JSON for rendering comparison tables or cards.
Why it matters: Demonstrates query shaping, DTO patterns, and user-driven API design.

4. Visual Policy Timeline

What: Gantt-style timeline of important policy milestones such as payment due dates, lock-in periods, and payouts.
How: GET /policies/timeline returns labeled timestamps; the frontend renders them in a timeline chart.
Why it matters: Highlights event modeling and clear temporal domain representation.

5. JWT-Based Authentication

What: Stateless authentication with signed JWT and secure password storage.
How: /auth/register and /auth/login endpoints with Spring Security filter chain to validate Authorization: Bearer <token>.
Why it matters: Implements secure, production-grade authentication ready for RBAC expansion.

6. CI/CD Pipeline (GitHub Actions + Helm)

What: Automatic testing, building, and deployment to Kubernetes on push to main.
How: GitHub Actions runs unit tests, builds Docker images, pushes to a container registry, and runs helm upgrade --install.
Why it matters: Ensures repeatable builds and automated deployments.

7. Observability (Prometheus + Grafana)

What: Golden-signal metrics (latency, errors, throughput) and custom business metrics (average ROI).
How: Micrometer + Actuator expose /actuator/prometheus, Prometheus scrapes metrics, Grafana visualizes them.
Why it matters: Provides both operational reliability and product analytics in one monitoring stack.

⸻

Tech Stack

Frontend
	•	React with Axios and React Router
	•	Material UI for responsive, production-grade components
	•	Chart.js / Recharts for visualizations

Backend
	•	Spring Boot (MVC, JPA, Security, WebClient)
	•	PostgreSQL for relational data integrity
	•	Micrometer + Spring Actuator for metrics
	•	JWT authentication with Spring Security and BCrypt

DevOps and Deployment
	•	Docker multi-stage builds for backend and frontend
	•	Kubernetes manifests with Helm charts
	•	GitHub Actions for CI/CD, including testing and automated deployment
	•	Prometheus + Grafana for metrics and dashboards

⸻

Architecture

Flow:
	1.	User logs in, backend issues JWT
	2.	React frontend uses Axios to call Spring Boot APIs with JWT in Authorization header
	3.	Backend aggregates data from PostgreSQL via JPA
	4.	Metrics exposed at /actuator/prometheus, scraped by Prometheus, displayed in Grafana
	5.	CI/CD pipeline runs tests, builds/pushes Docker images, deploys with Helm

Operational design:
	•	Stateless services with Kubernetes health/readiness probes
	•	Configurable via environment variables and Helm values
	•	Clear layering of controllers, services, repositories, and entities

⸻

Getting Started

Prerequisites
	•	Docker and Docker Compose
	•	Java 17 and Maven
	•	Node.js (optional for standalone frontend dev)
	•	kubectl and Helm (optional for Kubernetes deployment)

Local Run with Docker Compose

git clone https://github.com/<yourusername>/Insurelytics.git
cd Insurelytics
docker compose -f docker-compose.local.yml up --build

Default URLs
	•	Frontend: http://localhost:3000
	•	Backend: http://localhost:8081
	•	Grafana: http://localhost:3001 (default login: admin/admin)
	•	Prometheus: http://localhost:9090

Example API flow:

# Register
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","password":"demo"}'

# Login
TOKEN=$(curl -s -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","password":"demo"}' | jq -r .token)

# Access a protected route
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8081/dashboard/summary


⸻

Key REST Endpoints

Method	Endpoint	Purpose
GET	/dashboard/summary	Account summary data
POST	/calculator/returns	Calculate returns
GET	/policies/compare	Compare policies
GET	/policies/timeline	Timeline milestones
POST	/auth/login	JWT login
POST	/auth/register	Create user account


⸻

Testing

Backend

cd backend
mvn test

Frontend

cd frontend
npm test


⸻

Observability

System metrics:
	•	HTTP request rate and error rate
	•	Latency histograms (p50, p95, p99)
	•	JVM heap usage and garbage collection

Business metrics:
	•	Average ROI across all users
	•	Feature usage counts

Metrics are scraped by Prometheus and visualized in Grafana dashboards preconfigured for both technical and business KPIs.

⸻

CI/CD Pipeline

On push to main:
	•	Run backend and frontend unit tests
	•	Build and push Docker images to container registry
	•	Deploy to Kubernetes using Helm

Benefits:
	•	Reproducible builds
	•	Automated deployments
	•	Fast rollback via Helm release history

⸻

Security Highlights
	•	Stateless JWT authentication with signature validation
	•	BCrypt password hashing
	•	Minimal-privilege database credentials per environment
	•	Runtime secrets injection through CI/CD and Kubernetes secrets
	•	Configurable CORS and HTTP security headers

⸻

SRE and Production Considerations
	•	Kubernetes liveness/readiness probes for rolling updates
	•	Configurable CPU/memory requests and limits for autoscaling readiness
	•	Monitoring with Prometheus + Grafana for golden signals and business metrics
	•	Centralized logging to stdout, compatible with log aggregation stacks
	•	Potential resilience patterns (e.g., Resilience4j) for retries and circuit breaking

⸻

Roadmap
	•	AI-driven policy recommendations (Spring AI)
	•	Database migrations via Flyway
	•	OpenAPI/Swagger documentation for endpoints
	•	Advanced RBAC with an admin dashboard
	•	Dark mode UI and additional visualizations
