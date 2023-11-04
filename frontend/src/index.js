import React from "react";
import ReactDOM from "react-dom/client";
import './index.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import App from './App';
import ArticleDangerSearch from './ArticleDangerSearch';
import ArticleDangerCreate from './ArticleDangerCreate';
import ArticleDangerCreateWYSIWYG from './ArticleDangerCreateWYSIWYG';
import GreetingDanger from './GreetingDanger';
import ArticleSafeSearch from './ArticleSafeSearch';
import ArticleSafeCreate from './ArticleSafeCreate';
import GreetingSafe from './GreetingSafe';
 
const routing = (
  <BrowserRouter>
    <div>
      <Routes>
        <Route exact path="/" element={<App/>} />
        <Route path="/greetingDanger" element={<GreetingDanger/>} />
        <Route path="/articleDangerCreate" element={<ArticleDangerCreate/>} />
        <Route path="/articleDangerCreateWYSIWYG" element={<ArticleDangerCreateWYSIWYG/>} />
        <Route path="/articleDangerSearch" element={<ArticleDangerSearch/>} />
        <Route path="/greetingSafe" element={<GreetingSafe/>} />
        <Route path="/articleSafeCreate" element={<ArticleSafeCreate/>} />
        <Route path="/articleSafeSearch" element={<ArticleSafeSearch/>} />
      </Routes>
    </div>
  </BrowserRouter>
)
 
// ReactDOM.render(routing, document.getElementById('root'));
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  routing
);