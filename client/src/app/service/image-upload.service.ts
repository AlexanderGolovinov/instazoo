import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent} from '@angular/common/http';
import {Observable, Subscription} from 'rxjs';

const IMAGE_API = 'http://localhost:8080/api/image/';

@Injectable({
  providedIn: 'root'
})
export class ImageUploadService {

  constructor(private http: HttpClient) {
  }

  uploadImageToUser(file: File): Observable<any> {
    const uploadData = new FormData();
    uploadData.append('file', file);

    return this.http.post('http://localhost:8080/api/image/upload', uploadData);
  }

  getProfileImage(): Observable<any> {
    return this.http.get(IMAGE_API + 'profileImage');
  }
}
