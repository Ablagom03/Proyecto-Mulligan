import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { Prueba } from './app/prueba/prueba';

bootstrapApplication(Prueba, appConfig)
  .catch((err) => console.error(err));
