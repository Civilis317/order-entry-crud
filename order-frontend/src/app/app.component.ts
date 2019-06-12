import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {StateService} from "./services/state.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title: string = 'order-frontend';

  displayName: string;

  constructor(
    private route: ActivatedRoute,
    private stateService: StateService
  ) {
  }

  ngOnInit() {
    this.route.queryParams.subscribe((queryParams: Params) => {
      if (queryParams.hasOwnProperty("displayName")) {
        this.displayName = queryParams["displayName"];
        this.stateService.pushDisplayname(this.displayName);
      }
    })
  }
}
