USE [LifeGreen]
GO

/****** Object:  Table [dbo].[Clientes]    Script Date: 21/11/2024 02:29:19 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Clientes](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [varchar](50) NOT NULL,
	[CPF] [varchar](14) NOT NULL,
	[telefone] [varchar](20) NOT NULL,
	[email] [varchar](40) NOT NULL,
	[senha] [varchar](20) NOT NULL,
	[cep] [varchar](10) NOT NULL,
	[logradouro] [varchar](120) NOT NULL,
	[numero] [varchar](7) NOT NULL,
	[complemento] [varchar](50) NULL,
	[localidade] [varchar](30) NOT NULL,
	[bairro] [varchar](20) NOT NULL,
	[estado] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

