import { Component, OnInit } from '@angular/core';
import { GenericResponse } from '../GenericResponse';
import { Hero } from '../hero';
import { HeroService } from '../hero.service';
import { MessageService } from '../message.service';


@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.css']
})
export class HeroesComponent implements OnInit {

  heroes: Hero[] = [];
  constructor(private heroService: HeroService) { }

  ngOnInit(): void {
    this.getHeroes();
  }

  getHeroes(): void {
    this.heroService.getHeroes().subscribe(response => {
      this.heroes = response.body.heroes;
    });
  }
  add(name: string): void {
    name = name.trim();
    if (!name) { return; } //if name is null, then return
    //handler for name to Hero class
    this.heroService.addHero({ name } as Hero).subscribe(hero => {
      this.heroes.push(hero);
    });
  }
  delete(hero: Hero): void {
    this.heroService.deleteHero(hero.id).subscribe(response =>{
      const deletedHero: Hero = response;
      this.heroes = this.heroes.filter(h => h.id !== deletedHero.id);
    }
      
    );
    

  }

}
