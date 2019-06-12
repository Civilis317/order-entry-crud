import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment";
import {StateService} from "../services/state.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  displayName: string;

  logout_url: string = `${environment.backend}/${environment.slo_url}`;

  constructor(private stateService: StateService) { }

  ngOnInit() {
    this.stateService.getDisplayname().subscribe(s => {
      this.displayName = s;
    })

  }

}
