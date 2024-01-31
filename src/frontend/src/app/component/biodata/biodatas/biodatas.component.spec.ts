import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiodatasComponent } from './biodatas.component';

describe('BiodatasComponent', () => {
  let component: BiodatasComponent;
  let fixture: ComponentFixture<BiodatasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BiodatasComponent]
    });
    fixture = TestBed.createComponent(BiodatasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
