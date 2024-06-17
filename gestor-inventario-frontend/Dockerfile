FROM node:alpine

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN npm install -g @angular/cli

#Hago el --force, por que hay algunas librerias que me dan conflicto por las versiones
RUN npm install --force

CMD ["ng", "serve", "--host", "0.0.0.0"]
