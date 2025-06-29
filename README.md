
# 🪙 Coin Challenge Backend

This is the backend service for the coin challenge solution: a tool that computes the **minimum number of coins or notes** required to reach a target amount.  

Built using **Java + Dropwizard**, it exposes a REST API, includes **unit tests**, and is fully containerized with **Docker**. The service is hosted on **AWS EC2** using **Terraform**, **Ansible**, and **GitHub Actions**.

---

## 🚀 Features

- REST API with [Dropwizard](https://www.dropwizard.io/)
- Efficient greedy algorithm for coin change
- Input validation & descriptive error responses
- Unit tests for core logic
- Dockerfile for containerization
- Deployed and accessible via AWS EC2

---

## 🧪 Running Tests

Use Maven to run tests:

```bash
mvn test
````

Unit tests cover:

* Edge cases (e.g., zero input, negative values)
* Coin distribution logic
* Invalid denomination formats

---

## 🛠 API Usage

### **POST** `/coins/min`

**Request**:

```json
{
  "targetAmount": 8.75,
  "denominations": [1, 0.5, 0.2, 0.05]
}
```

**Success Response**:

```json
{
  "coins": [1, 1, 1, 1, 0.5, 0.2, 0.05]
}
```

**Error Response**:

```json
{
  "error": "Target amount must be greater than 0"
}
```

---

## 🐳 Docker

### Build the Docker image

```bash
docker build -t coin-challenge-backend .
```

### Run the Docker container

```bash
docker run -p 8080:8080 coin-challenge-backend
```

Access the API at:
📍 `http://localhost:8080/api/coin/change`

---

## ☁️ AWS Hosting & CI/CD

This service is hosted on an **AWS EC2** instance with automated infrastructure and deployment managed by:

- **Terraform**: Provisions the EC2 instance, security groups, Elastic IP, and other cloud resources.
- **Ansible**: Installs Docker on the EC2 instance and pulls the latest backend Docker image.
- **GitHub Actions**: CI/CD workflow that:
  - Triggers on every push to `main`
  - Builds the Docker image
  - Pushes it to Docker Hub
  - Runs the Ansible playbook to deploy the updated container on the EC2 instance


## 🧾 License

MIT — Free for personal and commercial use.
