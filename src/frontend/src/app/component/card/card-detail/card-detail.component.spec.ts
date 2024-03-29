import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardDetailComponent } from './card-detail.component';

describe('CardComponent', () => {
  let component: CardDetailComponent;
  let fixture: ComponentFixture<CardDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardDetailComponent]
    });
    fixture = TestBed.createComponent(CardDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
