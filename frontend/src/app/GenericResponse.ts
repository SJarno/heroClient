export interface GenericResponse<T> {
    type: string,
    message: string,
    error: string
    body: T
}