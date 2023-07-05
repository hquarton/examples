import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TutorialsListComponent } from './tutorials-list.component';
import { TutorialService } from 'src/app/services/tutorial.service';
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { Observable, of } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Tutorial } from 'src/app/models/tutorial.model';

const mockTutorialService = {
  getAll():Observable<Tutorial[]>{
    return of([{id:1, title:"titre", description:"description", published:true}]);
  },
  deleteAll():Observable<any>{
    return of(undefined);
  },
  findByTitle(title: any):Observable<Tutorial[]>{
    return of([{id:1, title:"titre", description:"description", published:true}]);
  },
}

describe('TutorialsListComponent', () => {
  let component: TutorialsListComponent;
  let fixture: ComponentFixture<TutorialsListComponent>;
  let tutorialService: TutorialService;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [NoopAnimationsModule],
      declarations: [ TutorialsListComponent ],
      providers: [{provide: TutorialService, useValue: mockTutorialService}],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TutorialsListComponent);
    component = fixture.componentInstance;
    tutorialService = TestBed.inject(TutorialService);
    fixture.detectChanges();
  });

  it('should create', () => {
    // Given
    component.tutorials = [];
    jest.spyOn(tutorialService, "getAll").mockReturnValue(of([{id:1, title:"titre", description:"description", published:true}]));
    // When

    //Then
    expect(component).toBeTruthy();
    expect(tutorialService.getAll).toHaveBeenCalledTimes(0);
    expect(component.tutorials).toBeDefined();
    expect(component.tutorials.length).toEqual(0);
  });


  it('should ngOnInit', () => {
    // Given
    component.tutorials = [];
    jest.spyOn(tutorialService, "getAll").mockReturnValue(of([{id:1, title:"titre", description:"description", published:true}]));

    // When
    component.ngOnInit();

    //Then
    expect(tutorialService.getAll).toHaveBeenCalledTimes(1);
    expect(component.tutorials).toBeDefined();
    expect(component.tutorials.length).toEqual(1);
    expect(component.tutorials[0].id).toEqual(1);
    expect(component.tutorials[0].title).toEqual("titre");
    expect(component.tutorials[0].description).toEqual("description");
    expect(component.tutorials[0].published).toBeTruthy();
  });


  it('should retrieveTutorials', () => {
    // Given
    component.tutorials = [];
    jest.spyOn(tutorialService, "getAll").mockReturnValue(of([{id:1, title:"titre", description:"description", published:true}]));

    // When
    component.retrieveTutorials();

    //Then
    expect(tutorialService.getAll).toHaveBeenCalledTimes(1);
    expect(component.tutorials).toBeDefined();
    expect(component.tutorials.length).toEqual(1);
    expect(component.tutorials[0].id).toEqual(1);
    expect(component.tutorials[0].title).toEqual("titre");
    expect(component.tutorials[0].description).toEqual("description");
    expect(component.tutorials[0].published).toBeTruthy();
  });


  it('should refreshList', () => {
    // Given
    component.tutorials = [];
    component["currentTutorial"] = new Tutorial();
    component["currentIndex"] = 0;
    jest.spyOn(tutorialService, "getAll").mockReturnValue(of([{id:1, title:"titre", description:"description", published:true}]));

    // When
    component.refreshList();

    //Then
    expect(tutorialService.getAll).toHaveBeenCalledTimes(1);
    expect(component.tutorials).toBeDefined();
    expect(component.tutorials.length).toEqual(1);
    expect(component.tutorials[0].id).toEqual(1);
    expect(component.tutorials[0].title).toEqual("titre");
    expect(component.tutorials[0].description).toEqual("description");
    expect(component.tutorials[0].published).toBeTruthy();
    expect(component["currentTutorial"]).toEqual({});
    expect(component["currentIndex"]).toEqual(-1);
  });

  it('should setActiveTutorial', () => {
    // Given
    component.tutorials = [];
    component["currentTutorial"] = new Tutorial();
    component["currentIndex"] = 0;
    const tutorial = {id:1, title:"titre", description:"description", published:true};

    // When
    component.setActiveTutorial(tutorial, 10);

    //Then
    expect(component["currentTutorial"]).toBeDefined();
    expect(component["currentTutorial"].id).toEqual(1);
    expect(component["currentTutorial"].title).toEqual("titre");
    expect(component["currentTutorial"].description).toEqual("description");
    expect(component["currentTutorial"].published).toBeTruthy();
    expect(component["currentIndex"]).toEqual(10);
  });

  it('should removeAllTutorials', () => {
    // Given
    component.tutorials = [];
    component["currentTutorial"] = new Tutorial();
    component["currentIndex"] = 0;
    jest.spyOn(tutorialService, "deleteAll").mockClear();
    jest.spyOn(tutorialService, "getAll").mockReturnValue(of([{id:1, title:"titre", description:"description", published:true}]));

    // When
    component.removeAllTutorials();

    //Then
    expect(component["currentTutorial"]).toEqual({});
    expect(component["currentIndex"]).toEqual(-1);
    expect(tutorialService.deleteAll).toHaveBeenCalledTimes(1);
    expect(tutorialService.getAll).toHaveBeenCalledTimes(1);
    expect(component.tutorials).toBeDefined();
    expect(component.tutorials.length).toEqual(1);
    expect(component.tutorials[0].id).toEqual(1);
    expect(component.tutorials[0].title).toEqual("titre");
    expect(component.tutorials[0].description).toEqual("description");
    expect(component.tutorials[0].published).toBeTruthy();
  });

  it('should searchTitle', () => {
    // Given
    component.tutorials = [];
    component["currentTutorial"] = new Tutorial();
    component["currentIndex"] = 0;
    jest.spyOn(tutorialService, "findByTitle").mockReturnValue(of([{id:1, title:"titre", description:"description", published:true}]));

    // When
    component.searchTitle();

    //Then
    expect(component["currentTutorial"]).toEqual({});
    expect(component["currentIndex"]).toEqual(-1);
    expect(tutorialService.findByTitle).toHaveBeenCalledTimes(1);
    expect(component.tutorials).toBeDefined();
    expect(component.tutorials.length).toEqual(1);
    expect(component.tutorials[0].id).toEqual(1);
    expect(component.tutorials[0].title).toEqual("titre");
    expect(component.tutorials[0].description).toEqual("description");
    expect(component.tutorials[0].published).toBeTruthy();
  });
});
