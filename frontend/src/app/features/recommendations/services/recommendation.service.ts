import { Injectable } from '@angular/core';
import { Observable, forkJoin, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { Course } from '../../../models/course.model';
import { Lesson } from '../../../models/lesson.model';
import { RecommendationPanel, RecommendedTopic } from '../../../models/recommendation.model';
import { CourseService } from '../../../core/services/course.service';

interface CourseLessonsContext {
	course: Course;
	lessons: Lesson[];
}

@Injectable({
	providedIn: 'root'
})
export class RecommendationService {
	constructor(private readonly courseService: CourseService) {}

	getRecommendationPanel(): Observable<RecommendationPanel> {
		return this.courseService.getCourses().pipe(
			switchMap((courses) => {
				const enrolledCourses = courses.filter((course) => course.enrolled);

				if (!enrolledCourses.length) {
					return of(this.buildEmptyPanel());
				}

				const requests = enrolledCourses.map((course) =>
					this.courseService.getLessonsByCourse(course.id).pipe(
						map((lessons) => ({
							course,
							lessons: [...lessons].sort((a, b) => a.order - b.order)
						}))
					)
				);

				return forkJoin(requests).pipe(map((contexts) => this.buildPanel(contexts)));
			})
		);
	}

	private buildPanel(contexts: CourseLessonsContext[]): RecommendationPanel {
		const nextCandidates: RecommendedTopic[] = [];
		const reinforcementCandidates: RecommendedTopic[] = [];

		const allLessonsCount = contexts.reduce((total, current) => total + current.lessons.length, 0);
		const averageProgress = contexts.length
			? Math.round(contexts.reduce((sum, current) => sum + current.course.progress, 0) / contexts.length)
			: 0;

		for (const context of contexts) {
			const nextLesson = context.lessons.find((lesson) => !lesson.completed);
			if (nextLesson) {
				nextCandidates.push({
					courseId: context.course.id,
					courseTitle: context.course.title,
					lessonId: nextLesson.id,
					lessonTitle: nextLesson.title,
					lessonOrder: nextLesson.order,
					progress: nextLesson.progress,
					reason: nextLesson.progress > 0
						? 'Retoma este tema para cerrarlo y consolidar avance.'
						: 'Es el siguiente paso natural de tu ruta de aprendizaje.'
				});
			}

			const weakLessons = context.lessons.filter((lesson) => lesson.progress > 0 && lesson.progress < 70);
			for (const lesson of weakLessons) {
				reinforcementCandidates.push({
					courseId: context.course.id,
					courseTitle: context.course.title,
					lessonId: lesson.id,
					lessonTitle: lesson.title,
					lessonOrder: lesson.order,
					progress: lesson.progress,
					reason: `Tiene ${lesson.progress}% de dominio. Repasarlo aumentara tu base para los siguientes temas.`
				});
			}
		}

		const nextSuggestedTopic = this.pickNextSuggestedTopic(nextCandidates, contexts);
		const reinforcementTopics = reinforcementCandidates
			.sort((a, b) => a.progress - b.progress || a.lessonOrder - b.lessonOrder)
			.slice(0, 5);

		return {
			generatedAt: new Date().toISOString(),
			enrolledCourses: contexts.length,
			lessonsTracked: allLessonsCount,
			averageProgress,
			nextSuggestedTopic,
			reinforcementTopics
		};
	}

	private pickNextSuggestedTopic(
		candidates: RecommendedTopic[],
		contexts: CourseLessonsContext[]
	): RecommendedTopic | null {
		if (!candidates.length) {
			return null;
		}

		// Prioriza continuar en cursos con mayor progreso total y en lecciones mas tempranas.
		const courseProgressMap = new Map<number, number>();
		for (const context of contexts) {
			courseProgressMap.set(context.course.id, context.course.progress);
		}

		const sortedCandidates = [...candidates].sort((a, b) => {
			const progressA = courseProgressMap.get(a.courseId) ?? 0;
			const progressB = courseProgressMap.get(b.courseId) ?? 0;
			return progressB - progressA || a.lessonOrder - b.lessonOrder;
		});

		return sortedCandidates[0] ?? null;
	}

	private buildEmptyPanel(): RecommendationPanel {
		return {
			generatedAt: new Date().toISOString(),
			enrolledCourses: 0,
			lessonsTracked: 0,
			averageProgress: 0,
			nextSuggestedTopic: null,
			reinforcementTopics: []
		};
	}
}
