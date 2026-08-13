import { useState } from "react";
import {
    Box,
    Button,
    Container,
    Paper,
    TextField,
    Typography
} from "@mui/material";
import { useNavigate } from "react-router-dom";

import authService from "../../services/authService";

function Login() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleSubmit = async (event) => {

        event.preventDefault();

        setError("");

        try {

            const data = await authService.login(
                username,
                password
            );

            console.log("Login successful:", data);

            navigate("/dashboard");

        } catch (error) {

            console.error(
                "Login failed:",
                error.response?.data || error.message
            );

            setError(
                error.response?.data?.message ||
                "Invalid username or password"
            );
        }
    };

    return (
        <Box
            sx={{
                minHeight: "100vh",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                backgroundColor: "#f5f6fa"
            }}
        >

            <Container maxWidth="sm">

                <Paper
                    elevation={4}
                    sx={{
                        padding: 4,
                        borderRadius: 3
                    }}
                >

                    <Typography
                        variant="h4"
                        align="center"
                        fontWeight="bold"
                        gutterBottom
                    >
                        Pharma MES
                    </Typography>

                    <Typography
                        variant="body1"
                        align="center"
                        color="text.secondary"
                        sx={{ mb: 3 }}
                    >
                        Manufacturing Execution System
                    </Typography>

                    <Box
                        component="form"
                        onSubmit={handleSubmit}
                    >

                        <TextField
                            fullWidth
                            label="Username"
                            margin="normal"
                            value={username}
                            onChange={(event) =>
                                setUsername(event.target.value)
                            }
                            required
                        />

                        <TextField
                            fullWidth
                            label="Password"
                            type="password"
                            margin="normal"
                            value={password}
                            onChange={(event) =>
                                setPassword(event.target.value)
                            }
                            required
                        />

                        {error && (
                            <Typography
                                color="error"
                                sx={{ mt: 2 }}
                            >
                                {error}
                            </Typography>
                        )}

                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            size="large"
                            sx={{ mt: 3 }}
                        >
                            Login
                        </Button>

                    </Box>

                </Paper>

            </Container>

        </Box>
    );
}

export default Login;