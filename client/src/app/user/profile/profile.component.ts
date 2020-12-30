import {Component, OnInit} from '@angular/core';
import {User} from '../../models/User';
import {TokenStorageService} from '../../service/token-storage.service';
import {UserService} from '../../service/user.service';
import {Post} from '../../models/Post';
import {PostService} from '../../service/post.service';
import {ImageUploadService} from '../../service/image-upload.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  isUserDataLoaded = false;
  isUserPostsLoaded = false;
  user: User;
  posts: Post [];
  selectedFile: File;
  userProfileImage: File;

  constructor(private tokenService: TokenStorageService,
              private postService: PostService,
              private http: HttpClient,
              private imageUploadService: ImageUploadService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getCurrentUser()
      .subscribe(data => {
        console.log(data);
        this.user = data;
        this.isUserDataLoaded = true;
      });

    this.postService.getPostForCurrentUser()
      .subscribe(data => {
        console.log(data);
        this.posts = data;
        this.isUserPostsLoaded = true;
      });

    this.imageUploadService.getProfileImage()
      .subscribe(data => {
        console.log(data);
        this.userProfileImage = data.imageBytes;
      });
  }

  onFileSelected(event): void {
    console.log(event);
    this.selectedFile = event.target.files[0];
  }

  onUpload(): void {
    if (this.selectedFile != null) {
      this.imageUploadService.uploadImageToUser(this.selectedFile)
        .subscribe(data => {
          window.location.reload();
        });
    }
  }

  formatImage(img: any): any {
    return 'data:image/jpeg;base64,' + img;
  }
}
