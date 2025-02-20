<div style="text-align:center">

# Proyecto para la Automatización de Edemco

<div style="width:320px;margin:auto">

![Logo oficial de Edemco](./edemco-frontend-react/public/Logo-removebg-preview.png)

</div>

</div>

<details style="font-size:1.2rem">
<summary style="font-size:1.5rem;font-weight:bold">Tabla de contenidos</summary>

- [Backend](#backend)

  - [Java](#java)
  - [Python](#python)

- [Frontend](#frontend)

  - [Instalación](#instalación-front)

- [Subir cambios a Github](#subir-cambios-a-github)

</details>

## Proyecto edemco

- [Diagrama de flujo edemco](https://excalidraw.com/#room=afa1a9ae958201ddddbc,gJy30kOFJ6QPT8EbcXmQ1g)

## Backend

El Backend está desarrollado con una arquitectura de microservicios

### Java

1. Puertos de los microservicios en Java
    - microservice-security: 8050
    - microservice-factura: 8060
    - microservice-remitentes: 8070
    - microservice-gateway: 8080
    - microservice-eureka: 8761
    - microservice-config: 8888
    - microservice-facturacion-especial: 9081
    - microservice-integracion: 9090
    - microservice-operadores: 9091
    - microservice-generation: 9092

### Python
1. Puertos de los microservicios de Python
   - microservice_historic-factories: 8090
   - microservice_template-facturas: 8091
   - microservice_get-invoice: 8092
   - microservice_upload-file: 8093
   - microservice_growatt-generation: 8094


## Frontend

El Frontend está desarrollado en ReactJS con atomic design

### Instalación Front

1. Instala los paquetes de NPM

```sh
npm install
```

2. Ejecuta el proyecto

```sh
npm run dev
```

## Github

### Subir cambios a Github

1. Crea tu rama, debes estar sobre la rama main

```sh
git checkout -b "nombre-de-tu-rama"
```

2. Añade los archivos al stage

```sh
git add .
```

2. Haz commit de tus cambios

```sh
git commit -m "feat: add invoices form"
```

3. Sube los cambios a tu rama

```sh
git push origin "nombre-de-tu-rama"
```

4. Haz un pull request desde Github en la pestaña `Pull requests` debes seleccionar el botón `New pull request`

5. Selecciona desde qué rama vas a hacer merge hacia la `main`

6. Configura el PR

- Selecciona el `Reviewer` (quién va a revisar los cambios)
- Selecciona el `Assignees` (quién realizó los cambios)
- Selecciona el `Label` (etiquetas para categorizar)
- Llena la `Descripción` de la PR

¡Y listo! Ya tus cambios serán Mergeados por el `Reviewer` ❤️.
