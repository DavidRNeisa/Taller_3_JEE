import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CourseService } from '../../../../core/services/course.service';
import { Assignment } from '../../../../models/assignment.model';

@Component({
	selector: 'app-submission-form',
	standalone: true,
	imports: [CommonModule, FormsModule, RouterLink],
	templateUrl: './submission-form.component.html',
	styleUrl: './submission-form.component.css'
})
export class SubmissionFormComponent implements OnInit {
	courseId = 0;
	assignmentId = 0;
	assignment: Assignment | null = null;
	comment = '';
	selectedFile: File | null = null;
	loading = true;
	submitting = false;
	submitSuccess = false;
	errorMessage = '';

	readonly allowedExtensions = ['pdf', 'doc', 'docx', 'zip', 'rar', 'txt'];
	readonly maxSizeBytes = 10 * 1024 * 1024;

	constructor(
		private route: ActivatedRoute,
		private router: Router,
		private courseService: CourseService
	) {}

	ngOnInit(): void {
		this.route.params.subscribe(params => {
			this.courseId = +params['courseId'];
			this.assignmentId = +params['assignmentId'];
			this.loadAssignment();
		});
	}

	private loadAssignment(): void {
		this.loading = true;
		this.errorMessage = '';

		this.courseService.getAssignmentById(this.courseId, this.assignmentId).subscribe({
			next: (assignment) => {
				this.assignment = assignment;
				this.loading = false;
			},
			error: () => {
				this.loading = false;
				this.errorMessage = 'No se pudo cargar la tarea para entregar.';
			}
		});
	}

	onFileSelected(event: Event): void {
		const input = event.target as HTMLInputElement;
		const file = input.files && input.files.length > 0 ? input.files[0] : null;

		if (!file) {
			this.selectedFile = null;
			return;
		}

		const validationError = this.validateFile(file);
		if (validationError) {
			this.errorMessage = validationError;
			this.selectedFile = null;
			input.value = '';
			return;
		}

		this.errorMessage = '';
		this.selectedFile = file;
	}

	private validateFile(file: File): string | null {
		if (file.size > this.maxSizeBytes) {
			return 'El archivo supera el limite de 10 MB.';
		}

		const extension = file.name.split('.').pop()?.toLowerCase() || '';
		if (!this.allowedExtensions.includes(extension)) {
			return `Formato no permitido. Usa: ${this.allowedExtensions.join(', ')}`;
		}

		return null;
	}

	get canSubmit(): boolean {
		return !!this.assignment && !!this.selectedFile && !this.submitting;
	}

	get formattedDueDate(): string {
		if (!this.assignment) {
			return '';
		}
		return new Date(this.assignment.dueDate).toLocaleString('es-ES', {
			day: '2-digit',
			month: 'long',
			year: 'numeric',
			hour: '2-digit',
			minute: '2-digit'
		});
	}

	submitWork(): void {
		if (!this.canSubmit || !this.assignment) {
			return;
		}

		this.submitting = true;
		this.errorMessage = '';

		this.courseService.submitAssignment(1, this.assignment.id, this.selectedFile!).subscribe({
			next: () => {
				this.submitting = false;
				this.submitSuccess = true;
			},
			error: () => {
				this.submitting = false;
				this.errorMessage = 'No se pudo registrar la entrega. Intenta nuevamente.';
			}
		});
	}

	backToAssignments(): void {
		this.router.navigate(['/courses', this.courseId, 'assignments']);
	}
}
