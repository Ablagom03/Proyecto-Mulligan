FROM node:24-alpine
WORKDIR /app
COPY . /usr/share/nginx/html/

RUN apk update && \
npm install -g @angular/cli

CMD ["sh"]

EXPOSE 4200
#CMD ["nginx", "-g", "daemon off;"]