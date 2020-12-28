import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../../service/token-storage.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  isLoggedIn = false;
  fullUsername: string;

  constructor(private tokenService: TokenStorageService) {
  }

  ngOnInit(): void {
    console.log(!!this.tokenService.getToken());

    this.isLoggedIn = !this.tokenService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenService.getUser();
      this.fullUsername = user.firstname + ' ' + user.lastname;
    }
  }

  logout(): void {
    this.tokenService.signOut();
    window.location.reload();
  }

}
