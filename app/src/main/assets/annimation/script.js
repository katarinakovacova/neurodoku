const container = document.getElementById("styled-grid");
const attemptsDisplay = document.getElementById("attempts");
let grid = [];
let shouldStop = false;
let attempts = 0;

function initGrid() {
  grid = Array(9).fill(0).map(() => Array(9).fill(0));
  container.innerHTML = "";
  for (let y = 0; y < 9; y++) {
    for (let x = 0; x < 9; x++) {
      const cell = document.createElement("div");
      cell.className = "number-cell";
      cell.dataset.x = x;
      cell.dataset.y = y;
      container.appendChild(cell);
    }
  }
  attempts = 0;
  updateAttempts();
}

function updateAttempts() {
  attemptsDisplay.textContent = `Attempts: ${attempts}`;
}

function stop() {
  shouldStop = true;
}

function start() {
  shouldStop = false;
  initGrid();
  fillGrid();
}

const delay = ms => new Promise(res => setTimeout(res, ms));

function isValid(grid, row, col, number) {
  for (let i = 0; i < 9; i++) {
    if (grid[row][i] === number || grid[i][col] === number) return false;
  }
  const startRow = row - row % 3, startCol = col - col % 3;
  for (let i = 0; i < 3; i++)
    for (let j = 0; j < 3; j++)
      if (grid[startRow + i][startCol + j] === number) return false;
  return true;
}

async function fillGrid() {
  for (let row = 0; row < 9; row++) {
    for (let col = 0; col < 9; col++) {
      if (grid[row][col] === 0) {
        const numbers = shuffle([1,2,3,4,5,6,7,8,9]);
        for (let num of numbers) {
          if (shouldStop) return false;

          attempts++;
          updateAttempts();

          if (isValid(grid, row, col, num)) {
            grid[row][col] = num;
            updateCell(row, col, num, "trying");
            await delay(200);

            if (await fillGrid()) {
              updateCell(row, col, num, "confirmed");
              return true;
            }

            grid[row][col] = 0;
            updateCell(row, col, "", "backtrack");
            await delay(200);
          }
        }
        return false;
      }
    }
  }
  return true;
}

function updateCell(row, col, value, className) {
  const cells = document.querySelectorAll(".number-cell");
  const cell = [...cells].find(c => c.dataset.x == col && c.dataset.y == row);
  if (cell) {
    cell.textContent = value;
    cell.className = `number-cell ${className}`;
  }
}

function shuffle(array) {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
}

initGrid();
