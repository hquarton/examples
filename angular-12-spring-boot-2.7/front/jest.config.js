module.exports = {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  moduleNameMapper: {
      "@app/(.*)$": "<rootDir>/src/app/$1",
      '^src/(.*)$': '<rootDir>/src/$1',
      '^app/(.*)$': '<rootDir>/src/app/$1',
      '^assets/(.*)$': '<rootDir>/src/assets/$1',
      '^environments/(.*)$': '<rootDir>/src/environments/$1',
  },
  transformIgnorePatterns: [
      '!node_modules/'
  ],
  testPathIgnorePatterns: [
      "<rootDir>/node_modules/",
      "<rootDir>/dist/",
      "/*.mock.data.ts"
  ],
  // verbose: true,
  collectCoverage: true,
  coverageDirectory: "coverage",
  collectCoverageFrom: ["src/**/*.ts"],
  coveragePathIgnorePatterns: [
    "/node_modules/",
    ".module.ts",
    "environment.*.ts",
    "<rootDir>/src/main.ts",
    "<rootDir>/src/polyfills.ts",
    "<rootDir>/src/assets/",
    "<rootDir>/src/app/core/tools",
    "<rootDir>/src/app/core/constants",
    "<rootDir>/src/app/core/reducers",
    "/*.mock.data.ts"
  ]
};
