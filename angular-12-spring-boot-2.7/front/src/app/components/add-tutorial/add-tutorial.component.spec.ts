import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddTutorialComponent } from './add-tutorial.component';
import { TutorialService } from 'src/app/services/tutorial.service';
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { of } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';

const mockTutorialService = {
  create: jest.fn()
}

describe('AddTutorialComponent', () => {
  let component: AddTutorialComponent;
  let fixture: ComponentFixture<AddTutorialComponent>;
  let tutorialService: TutorialService;

  beforeEach(async() => {
    TestBed.configureTestingModule({
      imports: [NoopAnimationsModule],
      declarations: [AddTutorialComponent ],
      providers: [{provide: TutorialService, useValue: mockTutorialService}],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddTutorialComponent);
    component = fixture.componentInstance;
    tutorialService = TestBed.inject(TutorialService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('saveTutorial', () => {
    // Given
    component.submitted = false;
    component.tutorial.title = "titre";
    component.tutorial.description = "description";
    jest.spyOn(tutorialService, "create").mockReturnValue(of({id:1, title:"titre", description:"description", published:true}));
    // When
    component.saveTutorial();

    // Then
    expect(tutorialService.create).toHaveBeenCalledWith(
      {
        title: "titre",
        description: "description"
      });
    expect(component.submitted).toBeTruthy();
  });


  it('newTutorial', () => {
    // Given
    component.submitted = true;

    // When
    component.newTutorial();

    // Then
    expect(component.submitted).toBeFalsy();
    expect(component.tutorial).toBeDefined();
    expect(component.tutorial.id).toBeUndefined();
    expect(component.tutorial.title).toEqual("");
    expect(component.tutorial.description).toEqual("");
    expect(component.tutorial.published).toBeFalsy();
  });
});
