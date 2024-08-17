import {FC} from "react";
import styles from './button.module.css'
import {clsx} from "clsx";
interface ButtonProps{
    className:string;
    content:string;
    onClick:() => void;
}
export const Button:FC<ButtonProps> = (props) => {
    const {className,content,onClick} = props
    return (
        <button className={clsx(styles.button__wrapper,className)} onClick={onClick}>
            {content}
        </button>
    );
};

