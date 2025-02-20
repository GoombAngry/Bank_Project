import React from "react";
import { Routes,Route } from "react-router-dom";
import { RouteConfig } from "./routerConfig";

export const AppRouter = () => {

    return (
        <Routes>
            {
            RouteConfig.map(
                (route: any,index: number) => 
                    (<Route key={index} path={route.path} element={route.element}/>)
            )}
        </Routes>
    );
}