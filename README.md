# bigu-backend

## Overview
Bigu is a ride-sharing application that allows users to share rides with other users. The bigu-backend application is a RESTful API that provides the backend functionality for 
[bigu-frontend](http://github.com/engsoft-ufcg-22-2/bigu-frontend).

## Prerequisites
Before you can run the Bigu backend application, please ensure that you have the following prerequisites satisfied:

- **Docker**. You can download Docker [here](https://www.docker.com/get-started).
- **An e-mail account**. The Bigu backend application uses e-mail to send notifications to users. You can use any e-mail service you like, as long as it supports SMTP.
- **A PostgreSQL database**. If you're deploying Bigu on the cloud, you'll need to set up a PostgreSQL database. You can download it [here](https://www.postgresql.org/download/).

## Installation
To install and run the Bigu backend application, please follow these steps:

1. Clone the repository to your local machine:
   ```
   git clone https://github.com/engsoft-ufcg-22-2/bigu-backend.git
   ```
2. Navigate to the project directory:
   ```
   cd bigu-backend
   ```
3. Build the Docker image:
   ```
   docker build -t bigu-backend:latest .
   ```
   
### Deploying bigu-backend locally
To simplify local deployments, we have provided a `docker-compose.yml` file that will deploy the bigu-backend application along with a PostgreSQL database. To deploy the application locally, follow the steps below:

1. Edit the email server environment variables in the `mail.env` file. You can use any e-mail service you like, as long as it supports SMTP.
   
2. Run the following command to deploy the application:
   ```
    docker-compose -f bigu-backend.yml -f db.yml up
    ```
   
### Deploying bigu-backend on the cloud
You can deploy the bigu-backend application on any cloud platform that supports Docker containers. To deploy the application on the cloud using Docker Compose, follow the steps below:

1. Edit the email server environment variables in the `mail.env` file. You can use any e-mail service you like, as long as it supports SMTP.
2. Edit the database environment variables in the `database.env` file. You can use any PostgreSQL database you like, as long as it is accessible from the cloud.
3. Run the following command to deploy the application:
   ```
   docker-compose -f bigu-backend.yml up
   ```

## Contributing
If you would like to contribute to the development of bigu-backend, please follow these steps:

1. Fork the repository on GitHub.
2. Create a new branch for your feature or bug fix.
3. Make the necessary changes in your branch.
4. Commit your changes and push the branch to your forked repository.
5. Submit a pull request to the main repository.

## License
TODO: Add license information.

## Troubleshooting
If you're running macOS, you might need to install the `gettext` package to use `envsubst`. You can install it using Homebrew:
```bash
brew install gettext
```