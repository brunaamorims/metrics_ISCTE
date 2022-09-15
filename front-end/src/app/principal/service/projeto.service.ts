import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http'
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { GITHUB_API } from "../../app.api"


@Injectable({
  providedIn: 'root'
})
export class ProjetoService {

  constructor(private http: HttpClient) {}

  getProjetos(nome: string, token: string, repository:string) : Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })
    
    return this.http.get(`${GITHUB_API}/repos/${nome}/${repository}/projects`, { headers: headers })
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  getColunas(id: string, token: string) : Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })
    return this.http.get(`${GITHUB_API}/projects/${id}/columns`, { headers: headers })
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  getRepositorios(nome: string, token: string) : Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })
    return this.http.get(`${GITHUB_API}/user/repos`, { headers: headers })
    
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  } 

  private handleError(error: any) { 
    return throwError(() => error);
  }
  
}
