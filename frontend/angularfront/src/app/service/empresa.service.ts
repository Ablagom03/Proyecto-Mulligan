import { HttpClient } from '@angular/common/http';
import { Empresa } from "../model/Empresa";
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {

url: string = "http://localhost:8080/empresa";

constructor(private http: HttpClient) {}

  getEmpresas(){
    return this.http.get<Empresa[]>(this.url);
  } 
}
