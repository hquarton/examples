import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TutorialDetailsComponent } from './tutorial-details.component';
import { TutorialService } from 'src/app/services/tutorial.service';
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { Observable, of } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Tutorial } from 'src/app/models/tutorial.model';
import { RouterTestingModule } from '@angular/router/testing';
    import { HttpClientTestingModule } from '@angular/common/http/testing';

const mockTutorialService = {
  get(id:string):Observable<Tutorial>{
    return of({id:1, title:"titre", description:"description", published:true});
  },
  update(id:string,tutorial:Tutorial):Observable<any>{
    return of(undefined);
  },
  delete(id:string):Observable<any>{
    return of(undefined);
  },
}

const mockRoute = {
  route: {
    snapshot: {
      params: {
        id:1
      }
    },
  }
};

describe('TutorialDetailsComponent', () => {
  let component: TutorialDetailsComponent;
  let fixture: ComponentFixture<TutorialDetailsComponent>;
  let tutorialService: TutorialService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoopAnimationsModule,HttpClientTestingModule,
        RouterTestingModule.withRoutes([])],
      declarations: [ TutorialDetailsComponent ],
      providers: [
        {provide: TutorialService, useValue: mockTutorialService},
      ],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TutorialDetailsComponent);
    component = fixture.componentInstance;
    tutorialService = TestBed.inject(TutorialService);
    fixture.detectChanges();
  });

  it('should create', () => {
    // Given
    jest.spyOn(component, "getTutorial").mockImplementation(jest.fn());
    // when

    //then
    expect(component["getTutorial"]).toHaveBeenCalledTimes(0);
    expect(component).toBeTruthy();
    expect(component.message).toEqual("");
  });

  it('should ngOnInit', () => {
    // Given
    jest.spyOn(component, "getTutorial").mockImplementation(jest.fn());
    // when
    component.ngOnInit();

    //then
    expect(component["getTutorial"]).toHaveBeenCalledTimes(1);
    expect(component).toBeTruthy();
    expect(component.message).toEqual("");
  });

  it('should getTutorial', () => {
    // Given
    jest.spyOn(tutorialService, "get").mockReturnValue(of({id:1, title:"titre", description:"description", published:true}));

    // when
    component.getTutorial("1");

    //then
    expect(tutorialService.get).toHaveBeenCalledTimes(1);
    expect(component["currentTutorial"]).toBeDefined();
    expect(component["currentTutorial"].id).toEqual(1);
    expect(component["currentTutorial"].title).toEqual("titre");
    expect(component["currentTutorial"].description).toEqual("description");
    expect(component["currentTutorial"].published).toBeTruthy();
  });


  it('should updatePublished', () => {
    // Given
    component.currentTutorial = {id :"1", title :"new title", description:"new description", published :false};
    jest.spyOn(tutorialService, "update").mockReturnValue(of({id:1, title:"titre", description:"description", published:true}));

    // when
    component.updatePublished(false);

    //then
    expect(tutorialService.update).toHaveBeenCalledTimes(1);
    expect(tutorialService.update).toHaveBeenCalledWith("1", {title :"new title", description:"new description", published :false});
    expect(component.message).toEqual("The status was updated successfully!");
  });


  it('should updateTutorial', () => {
    // Given
    component.currentTutorial = {id :"1", title :"new title", description:"new description", published :false};
    jest.spyOn(tutorialService, "update").mockReturnValue(of({id:1, title:"titre", description:"description", published:true}));

    // when
    component.updateTutorial();

    //then
    expect(tutorialService.update).toHaveBeenCalledTimes(1);
    expect(tutorialService.update).toHaveBeenCalledWith("1", {id :"1", title :"new title", description:"new description", published :false});
    expect(component.message).toEqual("This tutorial was updated successfully!");
  });
});
