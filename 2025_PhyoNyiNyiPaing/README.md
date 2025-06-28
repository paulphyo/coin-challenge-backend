## 🐳 Docker Setup: Coin Challenge Backend

This project uses Docker to containerize the Dropwizard-based backend application.

### 📦 Build the Docker Image

Make sure you are in the root directory where your `Dockerfile` is located, then run:

```bash
docker build -t coin-challenge-backend .
```

This will build a Docker image named `coin-challenge-backend`.

### 🚀 Run the Docker Container

Start the container and expose it on port `8080`:

```bash
docker run -p 8080:8080 coin-challenge-backend
```

The backend will now be accessible at:
📍 [http://localhost:8080](http://localhost:8080)

