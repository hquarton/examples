import { TestBed } from '@angular/core/testing';
import { TutorialService } from './tutorial.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';

describe('TutorialService', () => {
  let service: TutorialService;
  let httpMock: HttpTestingController;
  const baseUrl = 'http://localhost:8080/api/tutorials';
  const tutorial = {id:1, title:"titre", description:"description", published:true};

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,HttpClientModule]
    });
    service = TestBed.inject(TutorialService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it("should getAll", () => {
    service
      .getAll()
      .subscribe((value$) => expect(value$).toEqual("testSuccess"));

    const request = httpMock.expectOne(
      (req) => req.method === "GET" && req.url === baseUrl
    );
    request.flush("testSuccess");
  });

  it("should get", () => {
    service
      .get("1")
      .subscribe((value$) => expect(value$).toEqual("testSuccess"));

    const request = httpMock.expectOne(
      (req) => req.method === "GET" && req.url === baseUrl+"/1"
    );
    request.flush("testSuccess");
  });

  it("should create", () => {
    service
      .create(tutorial)
      .subscribe((value$) => expect(value$).toEqual("testSuccess"));

    const request = httpMock.expectOne(
      (req) => req.method === "POST" && req.url === baseUrl && req.body === tutorial
    );
    request.flush("testSuccess");
  });

  it("should create", () => {
    service
      .update(1,tutorial)
      .subscribe((value$) => expect(value$).toEqual("testSuccess"));

    const request = httpMock.expectOne(
      (req) => req.method === "PUT" && req.url === baseUrl+"/1" && req.body === tutorial
    );
    request.flush("testSuccess");
  });

  it("should delete", () => {
    service
      .delete("1")
      .subscribe((value$) => expect(value$).toEqual("testSuccess"));

    const request = httpMock.expectOne(
      (req) => req.method === "DELETE" && req.url === baseUrl+"/1"
    );
    request.flush("testSuccess");
  });

  it("should deleteAll", () => {
    service
      .deleteAll()
      .subscribe((value$) => expect(value$).toEqual("testSuccess"));

    const request = httpMock.expectOne(
      (req) => req.method === "DELETE" && req.url === baseUrl
    );
    request.flush("testSuccess");
  });

  it("should findByTitle", () => {
    service
      .findByTitle("titre")
      .subscribe((value$) => expect(value$).toEqual("testSuccess"));

    const request = httpMock.expectOne(
      (req) => req.method === "GET" && req.url === baseUrl+"?title=titre"
    );
    request.flush("testSuccess");
  });
});
