import {Component, OnInit} from '@angular/core';
import {User} from '../../models/User';
import {TokenStorageService} from '../../service/token-storage.service';
import {UserService} from '../../service/user.service';
import {PostService} from '../../service/post.service';
import {ImageUploadService} from '../../service/image-upload.service';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {EditUserComponent} from '../edit-user/edit-user.component';
import {NotificationService} from '../../service/notification.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  isUserDataLoaded = false;
  user: User;
  selectedFile: File;
  userProfileImage: File;

  constructor(private tokenService: TokenStorageService,
              private postService: PostService,
              private dialog: MatDialog,
              private notificationService: NotificationService,
              private imageUploadService: ImageUploadService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getCurrentUser()
      .subscribe(data => {
        this.user = data;
        this.isUserDataLoaded = true;
      });

    this.imageUploadService.getProfileImage()
      .subscribe(data => {
        this.userProfileImage = data.imageBytes;
      });
  }

  onFileSelected(event): void {
    this.selectedFile = event.target.files[0];
  }

  onUpload(): void {
    if (this.selectedFile != null) {
      this.imageUploadService.uploadImageToUser(this.selectedFile)
        .subscribe(data => {
          this.notificationService.showSnackBar('Profile Image updated successfully');
          window.location.reload();
        });
    }
  }

  openEditDialog(): void {
    const dialogUserEditConfig = new MatDialogConfig();
    dialogUserEditConfig.width = '400px';
    dialogUserEditConfig.data = {
      user: this.user
    };
    this.dialog.open(EditUserComponent, dialogUserEditConfig);
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }
}
