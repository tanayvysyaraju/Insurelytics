# Insurelytics

This repository contains a full–stack insurance analytics platform built with **Spring Boot** on the backend and **React** on the frontend.  It includes JWT–based user authentication, dashboards for summarising policies and investments, a real–time return calculator, a policy comparison engine and a visual policy timeline.  To help you deploy confidently, the project ships with a multi–stage Docker build, a Helm chart for Kubernetes, and a CI/CD pipeline wired through GitHub Actions.  Observability is baked in via Spring Boot Actuator, Micrometer with Prometheus, and a ready–to–import Grafana dashboard.

## Features

- **Account Summary Dashboard** – aggregate a user’s policies, investments, transactions and upcoming premiums.  The `/dashboard/summary` endpoint returns a single DTO with pre–computed totals and the frontend displays this data in charts and cards.
- **Real‑Time Return Calculator** – POST to `/calculator/returns` with an investment amount and duration to receive a compound–interest projection; results are graphed in the UI.
- **Policy Comparison Engine** – GET `/policies/compare` with a list of IDs and receive a side‑by‑side comparison of premiums, durations and expected returns.
- **Visual Policy Timeline** – GET `/policies/timeline` to retrieve milestone dates (premium due dates, lock‑in periods, payout points) for each policy.
- **JWT Authentication** – register and login endpoints issue signed tokens which must be supplied to all secured routes.  Tokens expire after a configurable period and passwords are hashed with BCrypt.
- **Observability** – Micrometer exposes application metrics at `/actuator/prometheus`.  A sample Grafana dashboard (JSON) is provided in the `grafana` directory.
- **DevOps‑ready** – `Dockerfile` and `docker-compose.local.yml` allow you to run the full stack locally.  The `helm` directory contains a Helm chart that deploys the backend and frontend with sensible defaults, including resource requests, liveness/readiness probes and environment variables for connecting to your database.  The `.github/workflows` directory includes a GitHub Actions workflow that runs tests, builds and pushes Docker images and performs a `helm upgrade` to your cluster.

## Getting Started

### Prerequisites

- Java 17 (for the Spring Boot backend)
- Node.js 18 or later (for the React frontend)
- Docker and Docker Compose

### Running Locally

To start the database, backend, frontend, Prometheus and Grafana locally, run:

```bash
cd insurelytics-pro
docker compose -f docker-compose.local.yml up --build
```

This will expose the frontend at http://localhost:8080, the backend at http://localhost:8081, Prometheus at http://localhost:9090 and Grafana at http://localhost:3000 (default login: `admin` / `admin`).

Alternatively you can run the backend and frontend separately for development with hot–reload:

```bash
# start Postgres
docker compose -f docker-compose.local.yml up -d db

# start backend
cd backend
./mvnw spring-boot:run

# start frontend
cd ../frontend
npm install
npm run dev
```

### Kubernetes Deployment

The `helm/` directory contains a simple Helm chart that deploys the backend and frontend.  To deploy to your cluster:

```bash
cd insurelytics-pro/helm
helm upgrade --install insurelytics . -n <namespace> --create-namespace \
  --set backend.image.tag=<backend-image-tag> \
  --set frontend.image.tag=<frontend-image-tag>
```

You will need to provide a Postgres instance (or use your cloud provider’s database service) and set the appropriate environment variables (see `values.yaml`).

### Continuous Deployment

The GitHub Actions workflow located at `.github/workflows/main.yml` demonstrates how to automate testing, build, image push and Helm deployment on every push to `main`.  Store your secrets (Docker registry credentials, kubeconfig, etc.) in GitHub secrets.  This workflow is designed to work with GHCR but can be adapted to any OCI registry.

## Directory Structure

```
insurelytics-pro/
├── backend/        # Spring Boot application
├── frontend/       # React client
├── helm/           # Helm chart for Kubernetes deployment
├── grafana/        # Sample Grafana dashboard
├── docker-compose.local.yml  # Local dev environment
└── .github/workflows/   # CI/CD pipeline
```

## License

This project is provided for educational purposes and carries no warranty.  Use it as a starting point for your own applications.