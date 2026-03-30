# PBL-01 Flood-Fill

## Explicação do Algoritmo

### Lógica central (igual para Pilha e Fila)

1. Verifica se o pixel inicial possui a **cor de fundo** → se não, pula
2. **Empilha/Enfileira** o pixel inicial
3. Inicia o **loop principal**:
   - Desempilha/Desenfileira o próximo pixel
   - Verifica se está **dentro dos limites** da imagem
   - Verifica se a **cor é igual ao fundo** original
   - Se ambas condições são atendidas: **pinta** o pixel (ponto) com a nova cor
   - **Empilha/Enfileira os 4 vizinhos** (cima, baixo, esquerda, direita)
4. Repete até a estrutura estar vazia

### Diferença entre Pilha e Fila
- **Pilha**: O último vizinho inserido é processado primeiro → expande em profundidade. O preenchimento parece "serpentear" pela imagem.
- **Fila**: O primeiro vizinho inserido é processado primeiro → expande em ondas. O preenchimento cresce em camadas circulares a partir do ponto inicial.

> *"Pixels de cores diferentes do que a cor de fundo armazenada inicialmente são empilhados/enfileirados, mas nunca são pintados, pois não atendem as condições da checagem."*

---

### Estrutura de Pastas

```
floodfill/
├── img.png                          ← Imagem inicial armazenada (opção 0)
├── img_0.png                        ← Imagem inicial armazenada (opção 1)
├── img_1.png                        ← Imagem inicial armazenada (opção 2)
├── img_2.png                        ← Imagem inicial armazenada (opção 3)
├── src/v2
│    ├── Main.java                          ← Ponto de entrada do programa
│    ├── algorithm/                         ← Pasta separando classes do algorítmo
│    │    ├── ConfigDialog.java             ← Configuração do formulário do canvas
│    │    ├── IFloodFill.java               ← Interface que guarda os métodos de fill() e forceDisplayUpdate()
│    │    ├── FloodFillCanvas.java          ← Cria o canvas para o FlodFill- BufferedImage e JPanel
│    │    ├── AbstractFloodFill.java        ← Classe abstrata que armazena os dados e metodos genericos não relacionados ao algoritmo
│    │    ├── FloodFillStack.java           ← Classe que herdará AbstractFloodFill e implementará o método fillWithStack()
│    │    └── FloodFillQueue.java           ← Classe que herdará AbstractFloodFill e implementará o método fillWithQueue()
│    ├── LinkedList.java              ← Lógica de lista encadeada
│    ├── Node.java                    ← Nó genérico encadeado (base de Pilha e Fila)
│    ├── Stack.java                   ← Lógica da estrutura Pilha LIFO 
│    ├── Queue.java                   ← Lógica da estrutura Fila FIFO 
│    └── Point.java                   ← Representa uma coordenada (x, y)
└── output/
    ├── snapshots/                 ← Salva frames (snapshots) de Queue (Pilha e Fila)
    └── final/                     ← Snapshot do Resultado final
```
