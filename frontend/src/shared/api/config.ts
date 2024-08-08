import axios from 'axios'

export const $host = axios.create({
    withCredentials: true,
    baseURL: import.meta.env.VITE_REACT_APP_API_URL
})
export const $auth_host = axios.create({
    withCredentials: true,
    baseURL: import.meta.env.VITE_REACT_APP_API_URL
})

class AuthResponse {
}

$host.interceptors.response.use(
    (config) => config,
    async (error) => {
        const originalRequest = error.config
        if (error.response && error.response.status === 401 && error.config && !error.config._isRetry) {
            originalRequest._isRetry = true
            try {
                const response = await axios.post<AuthResponse>(`${import.meta.env.VITE_REACT_APP_API_URL}/auth/refresh`, {}, {withCredentials: true})
                localStorage.setItem('token', response.data.accessToken)
                return $host.request(originalRequest)
            } catch (e) {
                console.log('Ошибка обновления токена или токен истек')
                throw error
            }
        }
        throw error
    }
);

$host.interceptors.request.use((config) => {
    if(localStorage.getItem('token')){
        config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`
    }
    return config
})
