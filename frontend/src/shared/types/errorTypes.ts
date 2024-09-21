export interface ErrorType {
    readonly message: string
    readonly response: {
        readonly status?: string
    }
}

export interface KnownErrorType {
    readonly response?: {
        data?: {
            message:string
        }
    }
}

export interface RejectedDataType {
    readonly messageError: string
}
