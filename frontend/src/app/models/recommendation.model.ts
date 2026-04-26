export interface RecommendedTopic {
	courseId: number;
	courseTitle: string;
	lessonId: number;
	lessonTitle: string;
	lessonOrder: number;
	progress: number;
	reason: string;
}

export interface RecommendationPanel {
	generatedAt: string;
	enrolledCourses: number;
	lessonsTracked: number;
	averageProgress: number;
	nextSuggestedTopic: RecommendedTopic | null;
	reinforcementTopics: RecommendedTopic[];
}
