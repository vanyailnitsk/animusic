import React from 'react';
import styles from './style.module.css'
const Color = ({color,setColor} : {color:string,setColor:(color:string)=>void}) => {
    return (
            <div style={{background:color}}  className={styles.colored__square} onClick={() => setColor(color)}></div>
    );
};

export default Color;