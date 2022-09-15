import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http'
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { BACK_API } from "../../app.api"


@Injectable({
  providedIn: 'root'
})
export class BackService {

  constructor(private http: HttpClient) {}

  getGrafico(resposta:any, tipo: string) : Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    })
    return this.http.post(`${BACK_API}/grafico?tipo=${tipo}`, resposta, { headers: headers })
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  private handleError(error: any) { 
    return throwError(() => error);
  }
  
}
