FROM node:24-alpine

WORKDIR /app

RUN npm install -g @angular/cli

COPY . .

WORKDIR /app/angularfront

RUN npm install

EXPOSE 4200
CMD ["ng", "serve", "--host", "0.0.0.0", "--port", "4200", "--poll", "2000"]