import { Tutorial } from "./tutorial.model";

describe('Tutorial', () => {
  it('should create an instance', () => {
    const tuto = new Tutorial();
    expect(tuto).toBeTruthy();
  });

  it('should create an instance when fields are not set', () => {
    const tuto = new Tutorial();
    expect(tuto.id).toBeUndefined();
    expect(tuto.title).toBeUndefined();
    expect(tuto.description).toBeUndefined();
    expect(tuto.published).toBeUndefined();
  });


  it('should create an instance when fields are set', () => {
    const tuto = {id:1, title:"titre", description:"description", published:true};
    expect(tuto.id).toEqual(1);
    expect(tuto.title).toEqual("titre");
    expect(tuto.description).toEqual("description");
    expect(tuto.published).toBeTruthy();
  });
});
