declare module '*.png' {
    const value: any;
    export default value;
}
declare module '*.jpg' {
    const value: any;
    export default value;
}
declare module '*.ico' {
    const value: any;
    export default value;
}
declare module '*.jpeg' {
    const value: any;
    export default value;
}
declare module "*.module.css";

/// <reference types="vite/client" />

interface ImportMetaEnv {
    readonly VITE_REACT_APP_API_URL: string;
}

interface ImportMeta {
    readonly env: ImportMetaEnv;
}
