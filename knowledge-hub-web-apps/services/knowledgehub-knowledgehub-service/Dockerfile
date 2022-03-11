# Use openjdk's 8-jre as the base image
FROM openjdk:8-jre

# Get the profile argument
ARG PROFILE=docker
ARG CONTAINER_BASE_DIRECTORY=/opt/apps/docker/container
ARG ARTIFACT_NAME
ARG SERVICE_PORT

# Environment variables
ENV PROFILE=${PROFILE} \
    CONTAINER_BASE_DIRECTORY=${CONTAINER_BASE_DIRECTORY} \
    ARTIFACT_NAME=${ARTIFACT_NAME}

# Copy the artifact jar
ADD target/${ARTIFACT_NAME} ${CONTAINER_BASE_DIRECTORY}/${ARTIFACT_NAME}

# Change permissions
RUN bash -c 'touch ${CONTAINER_BASE_DIRECTORY}/${ARTIFACT_NAME}'

# Expose the port
EXPOSE ${SERVICE_PORT}

# Command that will be executed to startup the container
CMD ["bash", "-c", "java -Dspring.profiles.active=$PROFILE -DXms512m -jar ${CONTAINER_BASE_DIRECTORY}/${ARTIFACT_NAME}"]
