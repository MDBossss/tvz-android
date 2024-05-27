import { config } from "dotenv";
import http from "http";
import express, { NextFunction, Request, Response } from "express";
import cors from "cors";

import carRoutes from "./routes/carRoutes";

config();

const app = express();
const httpServer = http.createServer(app);

//Middleware
app.use(cors());

//Routes
app.use("/api/cars", carRoutes);

//Error handling middleware
app.use((err: Error, req: Request, res: Response, next: NextFunction) => {
  console.error(err);
  res.status(500).json({ error: "Internal Server Error" });
});

//Start the server
const PORT = process.env.PORT || 3000;
httpServer.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
