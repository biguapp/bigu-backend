# bigu-backend

## Overview
Bigu is a ride-sharing application that allows users to share rides with other users. The bigu-backend application is a RESTful API that provides the backend functionality for 
[bigu-frontend](http://github.com/engsoft-ufcg-22-2/bigu-frontend).

## Prerequisites
Before you can run the Bigu backend application, please ensure that you have the following prerequisites satisfied:

- **Podman**. You can download Podman [here](https://podman.io). 
- **An e-mail account**. The Bigu backend application uses e-mail to send notifications to users. You can use any e-mail service you like, as long as it supports SMTP.

## Deploying the application
To install and run the Bigu backend application, please follow these steps:

1. Clone the repository to your local machine:
   ```
   git clone https://github.com/engsoft-ufcg-22-2/bigu-backend.git
   ```
2. Navigate to the project directory:
   ```
   cd bigu-backend
   ```
3. Build the container image:
   ```
   podman build -t bigu-backend:latest .
   ```
4. Deploy the Kubernetes cluster:
   ```
   podman play kube bigu-backend.yml
   ```
   
### Accessing the application
Once the application is deployed, you can see the available endpoints by accessing Swagger at:
```
http://localhost:8080/swagger-ui/index.html
```

### Stopping the application
To stop the application, run the following command:
```
podman kube play bigu-backend.yaml --down
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