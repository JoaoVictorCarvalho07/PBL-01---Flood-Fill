# PBL-01 Flood-Fill

## Explicação do Algoritmo

### Lógica central (igual para Pilha e Fila)

1. Verifica se o pixel inicial possui a **cor de fundo** → se não, encerra
2. **Empilha/Enfileira** o pixel inicial
3. Inicia o **loop principal**:
   - Desempilha/Desenfileira o próximo pixel
   - Verifica se está **dentro dos limites** da imagem
   - Verifica se a **cor é igual ao fundo** original
   - Se ambas condições são atendidas: **pinta** o pixel (ponto) com a nova cor
   - **Empilha/Enfileira os 4 vizinhos** (cima, baixo, esquerda, direita)
4. Repete até a estrutura estar vazia

### Diferença entre Pilha (DFS) e Fila (BFS)
- **Pilha**: O último vizinho inserido é processado primeiro → expande em profundidade (DFS). O preenchimento parece "serpentear" pela imagem.
- **Fila**: O primeiro vizinho inserido é processado primeiro → expande em ondas (BFS). O preenchimento cresce em camadas circulares a partir do ponto inicial.

> *"Pixels de cores diferentes do que a cor de fundo armazenada inicialmente são empilhados/enfileirados, mas nunca são pintados, pois não atendem as condições da checagem."*

---

### Estrutura de Pastas

```
floodfill/
├── img_0.png                     ← Imagem inicial armazenada (opção 1)
├── img_1.png                     ← Imagem inicial armazenada (opção 2)
├── circles.png                   ← Imagem inicial armazenada (opção 3)
└── src/v2
    ├── Main.java                     ← Ponto de entrada do programa
    ├── ConfigDialog.java             ← Configuração do formulário
    ├── IFloodFill.java               ← Interface que guarda os métodos de fillWith...()
    ├── FloodFillCanvas.java          ← Cira o canvas para o FlodFill- BufferedImage e JPanel
    ├── LinkedList.java               ← Lógica de lista 
    ├── Node.java                     ← Nó genérico encadeado (base de Pilha e Fila)
    ├── Stack.java                    ← Pilha LIFO - método fillWithStack()
    ├── Queue.java                    ← Fila FIFO - método fillWithQueue()
    ├── Point.java                    ← Representa uma coordenada (x, y)
    └── saida/
        ├── queue/                 ← Salva frames (snapshots) de Queue (Fila)
        ├── stack/                 ← Salva frames (snapshots) da Stack (Pilha)
        └── resultadoFinal.png     ← Snapshot do Resultado final
```
