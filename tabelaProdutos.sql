USE [LifeGreen]
GO

/****** Object:  Table [dbo].[Produtos]    Script Date: 21/11/2024 02:29:34 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Produtos](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Imagem] [varbinary](max) NULL,
	[Nome] [nvarchar](100) NOT NULL,
	[Quantidade] [int] NOT NULL,
	[Preco] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

