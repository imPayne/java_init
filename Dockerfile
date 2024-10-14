FROM mysql:latest

# Variables d'environnement MySQL
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=cdi_books
ENV MYSQL_USER=user1
ENV MYSQL_PASSWORD=azertycdi

# Expose le port 3306 pour MySQL
EXPOSE 3306
