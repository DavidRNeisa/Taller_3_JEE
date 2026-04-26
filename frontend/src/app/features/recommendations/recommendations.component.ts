import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { RecommendationPanel } from '../../models/recommendation.model';
import { RecommendationService } from './services/recommendation.service';

@Component({
	selector: 'app-recommendations',
	standalone: true,
	imports: [CommonModule, RouterLink],
	templateUrl: './recommendations.component.html',
	styleUrl: './recommendations.component.css'
})
export class RecommendationsComponent implements OnInit {
	panel: RecommendationPanel | null = null;
	loading = true;
	errorMessage = '';

	constructor(private readonly recommendationService: RecommendationService) {}

	ngOnInit(): void {
		this.recommendationService.getRecommendationPanel().subscribe({
			next: (panel) => {
				this.panel = panel;
				this.loading = false;
			},
			error: () => {
				this.errorMessage = 'No se pudieron cargar las recomendaciones. Intenta de nuevo.';
				this.loading = false;
			}
		});
	}
}
