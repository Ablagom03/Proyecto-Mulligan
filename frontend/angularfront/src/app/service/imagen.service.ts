import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Imagen } from '../model/Imagen';

@Injectable({
  providedIn: 'root'
})
export class ImagenService {
  url: string= "http://localhost:8080/imagen"
  constructor(private http: HttpClient) { 

  }

  insertaImagen(nombre : string, img : string){
    //TODO
  }

}
