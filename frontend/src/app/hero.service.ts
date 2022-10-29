import { Injectable } from '@angular/core';
import { Hero } from './hero';
import { HEROES } from './mock-heroes';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { GenericResponse } from './GenericResponse';
import { HeroesList } from './HeroesList';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HeroService {

  private baseUrl = environment.baseUrl
  private heroesUrl = "/api/heroes";
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private messageService: MessageService, private http: HttpClient) { }

  getHeroes(): Observable<GenericResponse<HeroesList>> {
    return this.http.get<GenericResponse<HeroesList>>(`${this.baseUrl}${this.heroesUrl}/all-heroes`)
      .pipe(
        tap(_ => this.log('fetched heroes')),
        catchError(this.handleError<any>('getHeroes'))
      );

  }
  getHero(id: number): Observable<GenericResponse<Hero>> {
    return this.http.get<GenericResponse<Hero>>(`${this.baseUrl}${this.heroesUrl}/hero/${id}`)
      .pipe(
        tap(_ => this.log(`Fetched hero by id ${id}`)),
        catchError(this.handleError<any>('Get hero by id'))
      );
  }
  updateHero(hero: Hero): Observable<GenericResponse<Hero>> {
    return this.http.put<GenericResponse<Hero>>(`${this.baseUrl}${this.heroesUrl}/update`, hero, this.httpOptions)
      .pipe(
        tap(_ => this.log(`updated hero id=${hero.id}`)),
        catchError(this.handleError<any>('updateHero'))
      );
  }
  addHero(hero: Hero): Observable<Hero> {
    const url = `${this.baseUrl}${this.heroesUrl}/create`
    return this.http.post<GenericResponse<Hero>>(url, hero, this.httpOptions).pipe(
      tap((response: GenericResponse<Hero>) => {
        const genericResponse: GenericResponse<Hero> = response
        this.log(genericResponse.message)
      }),
      map(response => {
        const hero: Hero = response.body;
        return hero
      }),
      catchError(this.handleError<any>('add hero'))
    );
  }
  deleteHero(id: number): Observable<Hero> {
    const url = `${this.baseUrl}${this.heroesUrl}/delete/${id}`
    return this.http.delete<GenericResponse<Hero>>(url, this.httpOptions).pipe(
      tap(response => {
        const genericResponse: GenericResponse<Hero> = response
        this.log(genericResponse.message)
      }),
      map(response => {
        const hero: Hero = response.body;
        return hero;
      }),
      catchError(this.handleError<any>('deleteHero'))
    )
  }
  searchHeroes(term: string): Observable<Hero[]> {
    let params = {"name": term};
    console.log('Test')
    if (!term.trim()) {
      return of([]);
    }
    return this.http.get<GenericResponse<HeroesList>>(`${this.baseUrl}${this.heroesUrl}/find-by-name`, { params: params }).pipe(
      tap(response => {
        const genericResponse: GenericResponse<HeroesList> = response;
        console.log(genericResponse);
        this.log(genericResponse.message);
      }),
      map(response => {
        const heroesList: HeroesList = response.body;
        return heroesList.heroes;
      }),
      catchError(this.handleError<any>('finding by name'))
    )

  }
  private log(message: string): void {
    this.messageService.add(`Heroservice: ${message}`);
  }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.log("Errors occured", error);
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };

  }
}
