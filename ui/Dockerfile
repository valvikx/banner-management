FROM node:lts-alpine
WORKDIR /app
COPY package*.json yarn.lock ./
RUN yarn install
COPY . .
EXPOSE 8090
CMD ["yarn", "run", "serve"]
